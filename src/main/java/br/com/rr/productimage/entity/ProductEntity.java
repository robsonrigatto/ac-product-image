package br.com.rr.productimage.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class ProductEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PARENT_PRODUCT_ID")
    private ProductEntity parentProduct;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "PRODUCT_IMAGE",
        joinColumns = @JoinColumn(name = "PRODUCT_ID"),
        inverseJoinColumns = @JoinColumn(name = "IMAGE_ID")
    )
    private List<ImageEntity> images = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "PRODUCT_RELATIONSHIP",
            joinColumns = @JoinColumn(name = "PARENT_PRODUCT_ID"),
            inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID"))
    private List<ProductEntity> childProducts = new ArrayList<>();

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

    public ProductEntity getParentProduct() { return parentProduct; }

    public void setParentProduct(ProductEntity parentProduct) { this.parentProduct = parentProduct; }

    public List<ImageEntity> getImages() { return images; }

    public void setImages(List<ImageEntity> images) { this.images = images; }

    public List<ProductEntity> getChildProducts() { return childProducts; }

    public void setChildProducts(List<ProductEntity> childProducts) { this.childProducts = childProducts; }
}
