package com.smashingboxes.ga_wrapper;

/**
 * Created by Tyler McCraw on 2/19/16.
 *
 * Generic Analytics Event object
 */
public class GaEvent {

    private String category;
    private String action;
    private String label;
    private String customDimension1;
    private String customDimension2;

    public GaEvent(String category, String action, String label) {
        this.category = category;
        this.action = action;
        this.label = label;
    }

    public GaEvent(String category, String action, String label, String customDimension1) {
        this.category = category;
        this.action = action;
        this.label = label;
        this.customDimension1 = customDimension1;
    }

    public GaEvent(String category, String action, String label, String customDimension1, String customDimension2) {
        this.category = category;
        this.action = action;
        this.label = label;
        this.customDimension1 = customDimension1;
        this.customDimension2 = customDimension2;
    }

    public String getCategory() {
        return category;
    }

    public String getAction() {
        return action;
    }

    public String getLabel() {
        return label;
    }

    public String getCustomDimension1() {
        return customDimension1;
    }

    public String getCustomDimension2() {
        return customDimension2;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setCustomDimension1(String customDimension1) {
        this.customDimension1 = customDimension1;
    }

    public void setCustomDimension2(String customDimension2) {
        this.customDimension2 = customDimension2;
    }
}
