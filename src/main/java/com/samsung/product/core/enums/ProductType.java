package com.samsung.product.core.enums;

public enum ProductType {
  SMARTPHONE("S"),
  TELEVISION("T"),
  REFRIGERATOR("R"),
  ;

  public final String code;

  ProductType(String code) {
    this.code = code;
  }

  public static ProductType fromCode(String code) {
    for (ProductType type : ProductType.values()) {
      if (type.code.equalsIgnoreCase(code)) {
        return type;
      }
    }

    throw new RuntimeException("Unexpected code = " + code);
  }
}
