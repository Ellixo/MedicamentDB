package com.ellixo.healthcare.repository;

import com.ellixo.healthcare.domain.Medicament;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MedicamentRepository extends ElasticsearchRepository<Medicament, String> {

    @Query("{" +
            "        \"match_phrase_prefix\": {" +
            "            \"_all\": {" +
            "                \"query\": \"?0\"," +
            "                \"slop\":  100" +
            "            }" +
            "        }" +
            "    }")
    Page<Medicament> searchAll(String criteria, Pageable pageable);
}
