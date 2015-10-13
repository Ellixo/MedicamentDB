package com.ellixo.healthcare.repository;

import com.ellixo.healthcare.domain.GroupeGenerique;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface GroupeGeneriqueRepository extends ElasticsearchRepository<GroupeGenerique, String> {
}
