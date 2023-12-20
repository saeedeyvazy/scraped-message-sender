package com.content.provider.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.sql.Date;

@Entity
@Table(name = "NEWS")
@Data
@ToString
public class ScrapedNews {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String url;
    private String title;
    private String text;
    @Column(name = "eitta_status")
    private String eittaStatus;

    @Column(name = "telegram_status")
    private String telegramStatus;
    private String img;
    private Date news_date;
}
