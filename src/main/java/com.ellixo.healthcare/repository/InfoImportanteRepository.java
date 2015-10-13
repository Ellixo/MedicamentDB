package com.ellixo.healthcare.repository;

import com.ellixo.healthcare.domain.InfoImportante;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface InfoImportanteRepository extends ElasticsearchRepository<InfoImportante, String> {
}
