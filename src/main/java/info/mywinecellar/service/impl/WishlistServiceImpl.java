/*
 * My-Wine-Cellar, copyright 2020
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 */

package info.mywinecellar.service.impl;

import info.mywinecellar.model.Wishlist;
import info.mywinecellar.repository.WishlistRepository;
import info.mywinecellar.service.WishlistService;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

/**
 * Wishlist service implementation
 */
@Service
public class WishlistServiceImpl implements WishlistService {

    @Inject
    private WishlistRepository wishlistRepository;

    @Override
    public Wishlist findByUser(Integer userId, Long id) {
        return wishlistRepository.findByUser(userId, id);
    }

    @Override
    public Wishlist findByWine(Integer userId, Long wineId) {
        return wishlistRepository.findByWine(userId, wineId);
    }

    @Override
    public Wishlist findById(Long aLong) {
        return wishlistRepository.findById(aLong).orElse(null);
    }

    @Override
    public Wishlist save(Wishlist object) {
        return wishlistRepository.save(object);
    }

    @Override
    public void delete(Wishlist object) {
        wishlistRepository.delete(object);
    }

}
