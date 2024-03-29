package com.aguusz.ecommerce.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Column(name = "photo_url")
    private String photoUrl;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "sub_brand_id", referencedColumnName = "id")
    private SubBrand subBrand;

    @OneToMany(mappedBy = "size")
    private Set<Size> sizes;

    @ManyToMany(mappedBy = "products")
    Set<ShoppingCart> shoppingCarts;

    @ManyToMany(mappedBy = "products")
    Set<PurchaseOrder> purchaseOrders;
}
