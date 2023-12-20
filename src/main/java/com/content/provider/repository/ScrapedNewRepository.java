package com.content.provider.repository;

import com.content.provider.model.ScrapedNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScrapedNewRepository extends JpaRepository<ScrapedNews, Integer> {
    List<ScrapedNews> findAllByEittaStatusIsNullOrderByIdDesc();
    List<ScrapedNews> findAllByTelegramStatusIsNullOrderByIdDesc();


}
