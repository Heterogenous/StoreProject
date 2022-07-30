package com.rain.entity;

/** 多表联合查询返回的DTO对象 cart + product.image **/
public class CartDTO extends BaseEntity{
    private Cart cart;
    private String image;

    private String title;

    private Long realPrice;

    public Long getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(Long realPrice) {
        this.realPrice = realPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
