package kitchenpos.menugroup.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.fixture.MenuGroupFixture;
import kitchenpos.menugroup.domain.MenuGroup;
import kitchenpos.menugroup.domain.MenuGroupRepository;
import kitchenpos.menugroup.dto.MenuGroupRequest;
import kitchenpos.menugroup.dto.MenuGroupResponse;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("메뉴 그룹 서비스 테스트")
@ExtendWith(MockitoExtension.class)
public class MenuGroupServiceTest {

    @Mock
    private MenuGroupRepository menuGroupRepository;

    @InjectMocks
    private MenuGroupService menuGroupService;

    @DisplayName("메뉴 그룹을 등록할 수 있다.")
    @Test
    void register() {
        // given
        final MenuGroup expected = MenuGroupFixture.of(1L, "대표메뉴");
        given(menuGroupRepository.save(any(MenuGroup.class))).willReturn(expected);

        final MenuGroupRequest request = MenuGroupFixture.ofRequest(expected.getName());

        // when
        final MenuGroupResponse response = menuGroupService.register(request);

        // then
        assertAll(
            () -> assertThat(response.getId()).isNotNull(),
            () -> assertThat(response.getName()).isEqualTo(expected.getName())
        );
    }

    @DisplayName("메뉴 그룹 이름이 null 또는 \"\"일 경우 메뉴 그룹을 등록할 수 없다.")
    @ParameterizedTest(name = "{displayName} [{index}] {argumentsWithNames}")
    @NullAndEmptySource
    void registerFailInvalidName(final String name) {
        // given
        final MenuGroupRequest request = MenuGroupFixture.ofRequest(name);

        // when
        ThrowableAssert.ThrowingCallable response = () -> menuGroupService.register(request);

        // then
        assertThatThrownBy(response).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 그룹 목록을 조회할 수 있다.")
    @Test
    void list() {
        // given
        final List<MenuGroup> expected = Arrays.asList(
            MenuGroupFixture.of(1L, "한마리메뉴"),
            MenuGroupFixture.of(2L, "두마리메뉴")
        );
        given(menuGroupRepository.findAll()).willReturn(expected);

        // when
        final List<MenuGroupResponse> response = menuGroupService.list();

        // then
        final List<Long> expectedIds = expected.stream()
            .map(MenuGroup::getId)
            .collect(Collectors.toList());
        final List<Long> actualIds = response.stream()
            .map(MenuGroupResponse::getId)
            .collect(Collectors.toList());
        assertAll(
            () -> assertThat(actualIds).containsExactlyElementsOf(expectedIds),
            () -> assertThat(response.size()).isEqualTo(2)
        );
    }
}