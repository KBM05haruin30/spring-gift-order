package gift.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderResponseDTO(


    @NotNull(message = "옵션 ID를 입력해야 합니다.")
    Long optionId,

    @NotNull(message = "차감할 수량을 입력해야 합니다.")
    @Min(value = 1, message = "차감할 수량은 0보다 커야 합니다.")
    Long quantity,

    @NotBlank(message = "메시지를 입력하세요.")
    String message
) {

}
