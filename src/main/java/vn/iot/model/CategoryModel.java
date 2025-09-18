package vn.iot.model;

import java.util.List;

import lombok.Data;
import vn.iot.enity.ProductEntity;
@Data
public class CategoryModel {
	private Long CategoryId;
	private String name;
	private List<ProductEntity> products;
	private boolean isEdit = false;
}
