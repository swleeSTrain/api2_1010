package org.zerock.api2.product.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.api2.common.dto.PageRequestDTO;
import org.zerock.api2.common.dto.PageResponseDTO;
import org.zerock.api2.product.dto.ProductListDTO;
import org.zerock.api2.product.service.ProductService;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Log4j2
public class ProductController {

    private final ProductService productService;

    @GetMapping("list")
    public PageResponseDTO<ProductListDTO> list(PageRequestDTO pageRequestDTO) {
        return productService.getList(pageRequestDTO);
    }

}
