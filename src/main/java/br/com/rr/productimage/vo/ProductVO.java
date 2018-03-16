package br.com.rr.productimage.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductVO implements Serializable {

    private Long id;
    private String description;
    private List<ProductVO> childProducts = new ArrayList<>();
    private List<ImageVO> images = new ArrayList<>();

    public ProductVO() {
        this.childProducts = new ArrayList<>();
        this.images = new ArrayList<>();
    }

    public ProductVO(String description) {
        this();
        this.description = description;
    }

    public ProductVO(Long id, String description) {
        this(description);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProductVO> getChildProducts() { return childProducts; }

    public void setChildProducts(List<ProductVO> childProducts) { this.childProducts = childProducts; }

    public List<ImageVO> getImages() { return images; }

    public void setImages(List<ImageVO> images) { this.images = images; }
}
