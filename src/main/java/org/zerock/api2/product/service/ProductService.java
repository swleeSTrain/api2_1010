package org.zerock.api2.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.api2.common.dto.PageRequestDTO;
import org.zerock.api2.common.dto.PageResponseDTO;
import org.zerock.api2.product.dto.ProductListDTO;
import org.zerock.api2.product.repository.ProductRepository;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public PageResponseDTO<ProductListDTO> getList(PageRequestDTO pageRequestDTO) {
        return productRepository.list(pageRequestDTO);
    }
}
