package com.samsung.util.entityconverter.product;

import com.samsung.product.core.enums.ProductType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ProductTypeAttributeConverter implements AttributeConverter<ProductType, String> {
  @Override
  public String convertToDatabaseColumn(ProductType attribute) {
    if (attribute == null) {
      return null;
    }
    return attribute.code;
  }

  @Override
  public ProductType convertToEntityAttribute(String dbData) {
    if (dbData == null) {
      return null;
    }
    return ProductType.fromCode(dbData);
  }
}
