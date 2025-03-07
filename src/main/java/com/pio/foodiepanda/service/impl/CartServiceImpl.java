package com.pio.foodiepanda.service.impl;

import com.pio.foodiepanda.constants.MessageConstant;
import com.pio.foodiepanda.dto.CartItemDTO;
import com.pio.foodiepanda.exception.ResourceNotFoundException;
import com.pio.foodiepanda.model.*;
import com.pio.foodiepanda.repository.*;
import com.pio.foodiepanda.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    Logger logger = Logger.getLogger(CartServiceImpl.class.getName());

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    /**
     * Retrieves the cart items for a given user.
     *
     * @param email the email of the user.
     * @return a list of CartItemDTO objects.
     */
    @Override
    public List<CartItemDTO> getCartItemForUser(String email) {
        logger.info(MessageConstant.FETCHING_CART_ITEMS_FOR_USER + email);
        User user = userRepository.findByEmail(email);
        if (user == null) {
            logger.severe(MessageConstant.USER_NOT_FOUND + email);
            throw new RuntimeException(MessageConstant.USER_NOT_FOUND);
        }

        Customer customer = user.getCustomer();
        if (customer == null) {
            logger.severe(MessageConstant.CUSTOMER_NOT_FOUND + email);
            throw new RuntimeException(MessageConstant.CUSTOMER_NOT_FOUND);
        }

        List<Cart> cartItems = cartRepository.findByCustomerId(customer.getCustomerID());
        return cartItems.stream()
                .map(cart -> {
                    MenuItem menuItem = menuItemRepository.findById(cart.getMenuItemId())
                            .orElseThrow(() -> new RuntimeException(MessageConstant.NO_MENU_ITEM_FOUND));
                    boolean isAvailable = menuItemRepository.findById(cart.getMenuItemId()).get().isAvailable();
                    return new CartItemDTO(cart.getCartId(), cart.getMenuItemId(), cart.getQuantity(), cart.getPrice(), menuItem.getName(), customer.getCustomerID(), cart.getRestaurant().getRestaurantId(),isAvailable );
                })
                .collect(Collectors.toList());
    }

    /**
     * Saves the cart items for a user.
     *
     * @param localCart the local cart items.
     * @param principal the principal of the user.
     */
    @Override
    public void saveCartItems(Map<Long, Integer> localCart, Principal principal) {
        String email = principal.getName();
        logger.info(MessageConstant.SAVING_CART_ITEM_FOR_USER + email);
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            logger.severe(MessageConstant.CUSTOMER_NOT_FOUND + email);
            throw new RuntimeException(MessageConstant.USER_NOT_FOUND + email);
        }

        List<Cart> existingCart = cartRepository.findByCustomerId(customer.getCustomerID());

        // Remove items from the database that are not in the local cart
        existingCart.forEach(existingItem -> {
            Long menuItemId = existingItem.getMenuItemId();
            if (!localCart.containsKey(menuItemId)) {
                cartRepository.delete(existingItem);
                logger.info(MessageConstant.REMOVED_ITEM_FROM_CART + menuItemId);
            }
        });

        // Update quantities for existing items or add new items
        localCart.forEach((menuItemId, quantity) -> {
            Cart existingItem = existingCart.stream()
                    .filter(item -> item.getMenuItemId().equals(menuItemId))
                    .findFirst()
                    .orElse(null);

            if (existingItem != null) {
                existingItem.setQuantity(quantity);
                cartRepository.save(existingItem);
                logger.info(MessageConstant.UPDATING_QUANTITY_FOR_ITEM + menuItemId);
            } else {
                MenuItem menuItem = menuItemRepository.findById(menuItemId)
                        .orElseThrow(() -> new RuntimeException(MessageConstant.NO_MENU_ITEM_FOUND));

                Cart newCartItem = new Cart();
                newCartItem.setCustomer(customer);
                newCartItem.setMenuItemId(menuItemId);
                newCartItem.setQuantity(quantity);
                newCartItem.setPrice(menuItem.getPrice());
                newCartItem.setRestaurant(menuItem.getRestaurant());
                cartRepository.save(newCartItem);
                logger.info(MessageConstant.ITEM_ADDED_TO_CART + menuItemId);
            }
        });
    }

    /**
     * Removes an item from the cart.
     *
     * @param principal  the principal of the user.
     * @param menuItemId the ID of the menu item to remove.
     * @return true if the item was removed successfully, false otherwise.
     */
    @Override
    public boolean removeItemFromCart(Principal principal, Long menuItemId) {
        try {
            String email = principal.getName();
            logger.info(MessageConstant.REMOVING_ITEM_FORM_CART_FOR_USER + email);
            User user = userRepository.findByEmail(email);
            Long customerId = user.getCustomer().getCustomerID();
            cartRepository.deleteByCustomerIdAndMenuItemId(customerId, menuItemId);
            logger.info(MessageConstant.ITEM_REMOVED + menuItemId);
            return true;
        } catch (Exception e) {
            logger.severe(MessageConstant.UNEXPECTED_ERROR + e.getMessage());
            return false;
        }
    }

    /**
     * Clears the cart for a user.
     *
     * @param principal the principal of the user.
     */
    @Override
    public void clearCartForUser(Principal principal) {
        String email = principal.getName();
        logger.info(MessageConstant.CLEARING_CART_FOR_USER + email);

        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            logger.severe(MessageConstant.CUSTOMER_NOT_FOUND + email);
            throw new ResourceNotFoundException(MessageConstant.USER_NOT_FOUND);
        }
        cartRepository.deleteByCustomerId(customer.getCustomerID());
        logger.info(MessageConstant.CLEARING_CART_FOR_USER + email);
    }

    /**
     * Converts a CartItemDTO to a Cart entity.
     *
     * @param dto      the CartItemDTO object.
     * @param customer the customer entity.
     * @return the Cart entity.
     */
    private Cart convertToEntity(CartItemDTO dto, Customer customer) {
        MenuItem menuItem = menuItemRepository.findById(dto.getMenuItemId())
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.NO_MENU_ITEM_FOUND));
        Restaurant restaurant = menuItem.getRestaurant();
        menuItem.setName(dto.getItemName());
        Cart cart = new Cart();
        cart.setMenuItemId(dto.getMenuItemId());
        cart.setQuantity(dto.getQuantity());
        cart.setPrice(dto.getPrice());
        cart.setCustomer(customer);
        cart.setRestaurant(restaurant);
        return cart;
    }
}