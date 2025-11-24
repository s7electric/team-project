package use_case.apply_promotion;

import entity.Promotion;

/**
 * Data access interface for fetching promotion codes.
 */
public interface PromotionDataAccessInterface {

    Promotion findByCode(String code);
}