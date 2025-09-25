package com.example.cloudnative.catalogws.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table("CATALOG")
public class CatalogEntity implements Serializable {
    @Id
    private long id;

    private String productId;

    private String productName;

    private Integer stock;

    private Integer unitPrice;

    private Date createdAt;
}
