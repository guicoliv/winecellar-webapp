/*
 * My-Wine-Cellar, copyright 2020
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 */

package info.mywinecellar.repository;

import info.mywinecellar.model.Tasted;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TastedRepository extends JpaRepository<Tasted, Long> {

    /**
     * @param userId userId
     * @param id     id
     * @return Tasted
     */
    @Query("SELECT t FROM Tasted t WHERE t.user.id = :userid AND t.id = :id")
    Tasted findByUser(@Param("userid") Integer userId, @Param("id") Long id);

    /**
     * @param userId userId
     * @param wineId wineId
     * @return Tasted
     */
    @Query("SELECT t FROM Tasted t WHERE t.user.id = :userid AND t.wine.id = :wineid")
    Tasted findByWine(@Param("userid") Integer userId, @Param("wineid") Long wineId);

}
