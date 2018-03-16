package br.com.rr.productimage.vo;

import java.io.Serializable;

public class ProductVO implements Serializable {

    private Long id;
    private String description;

    public ProductVO() {}

    public ProductVO(Long id, String description) {
        this(description);
        this.id = id;
    }

    public ProductVO(String description) {
        this.description = description;
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
}
