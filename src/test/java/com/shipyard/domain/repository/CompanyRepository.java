package com.shipyard.domain.repository;

import com.shipyard.domain.data.Company;

import java.util.List;

public interface CompanyRepository {
    List<Company> listAll();
}
