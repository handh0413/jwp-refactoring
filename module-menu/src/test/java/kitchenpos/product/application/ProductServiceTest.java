package kitchenpos.product.application;

import kitchenpos.product.domain.Product;
import kitchenpos.product.domain.ProductRepository;
import kitchenpos.product.dto.ProductRequest;
import kitchenpos.product.dto.ProductResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static kitchenpos.product.fixture.ProductFixture.강정치킨;
import static kitchenpos.product.fixture.ProductFixture.페퍼로니피자;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @DisplayName("상품 생성 성공 테스트")
    @Test
    void create_success() {
        // given
        ProductRequest 요청_상품 = ProductRequest.of("강정치킨", BigDecimal.valueOf(17_000));

        given(productRepository.save(any(Product.class))).willReturn(강정치킨);

        // when
        ProductResponse 생성된_상품 = productService.create(요청_상품);

        // then
        assertThat(생성된_상품).isEqualTo(ProductResponse.of(강정치킨));
    }

    @DisplayName("상품 생성 실패 테스트 - 상품 가격이 0보다 작음")
    @Test
    void create_failure_invalidPrice() {
        // given
        ProductRequest 요청_상품 = ProductRequest.of("강정치킨", BigDecimal.valueOf(-1));

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> productService.create(요청_상품));
    }

    @DisplayName("상품 목록 조회 테스트")
    @Test
    void list() {
        // given
        given(productRepository.findAll()).willReturn(Arrays.asList(강정치킨, 페퍼로니피자));

        // when
        List<ProductResponse> 조회된_상품_목록 = productService.list();

        // then
        Assertions.assertThat(조회된_상품_목록).containsExactly(ProductResponse.of(강정치킨), ProductResponse.of(페퍼로니피자));
    }
}