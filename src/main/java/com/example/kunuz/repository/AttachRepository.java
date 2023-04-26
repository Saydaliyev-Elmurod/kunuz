package com.example.kunuz.repository;

import com.example.kunuz.entity.AttachEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AttachRepository extends CrudRepository<AttachEntity,String > , PagingAndSortingRepository<AttachEntity,String > {
}
