package seominkim.puppyAlert.domain.puppy.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import seominkim.puppyAlert.domain.favoriteHost.dto.request.FavoriteHostRequest;
import seominkim.puppyAlert.domain.favoriteHost.dto.response.FavoriteHostResponse;
import seominkim.puppyAlert.domain.food.dto.response.FoodInfoResponse;
import seominkim.puppyAlert.domain.puppy.dto.request.EndMatchRequest;
import seominkim.puppyAlert.domain.puppy.dto.request.MatchRequest;
import seominkim.puppyAlert.domain.puppy.dto.response.MatchResponse;
import seominkim.puppyAlert.domain.puppy.service.PuppyService;
import seominkim.puppyAlert.domain.user.dto.request.CancelFoodRequest;
import seominkim.puppyAlert.domain.user.dto.response.CancelFoodResponse;
import seominkim.puppyAlert.domain.user.dto.response.UserInfoResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/puppy")
@Tag(name = "Puppy API", description = "정보 조회, 집밥 신청, 기록 조회, 관심 호스트 조회")
public class PuppyController {

    private final PuppyService puppyService;

    @Operation(summary = "전체 조회 (관리자용)")
    @GetMapping("/all")
    public List<UserInfoResponse> findAll() {
        return puppyService.findAll();
    }

    @Operation(summary = "신청 가능한 집밥 조회")
    @GetMapping("/food")
    public List<FoodInfoResponse> getAvailableFood(@RequestParam String puppyId){
        return puppyService.getAvailableFood(puppyId);
    }

    @Operation(summary = "집밥 신청")
    @PostMapping("/food")
    public MatchResponse matchFood(@RequestBody MatchRequest matchRequest){
        return puppyService.handleMatchRequest(matchRequest);
    }

    @Operation(summary = "집밥 취소")
    @PatchMapping("/food")
    public CancelFoodResponse cancelFood(@RequestBody CancelFoodRequest cancelFoodRequest){
        return puppyService.handleCancelRequest(cancelFoodRequest);
    }

    @Operation(summary = "집밥 식사 완료")
    @PostMapping("/end")
    public Long endMatch(@RequestBody EndMatchRequest endMatchRequest){
        return puppyService.handleEndMatchRequest(endMatchRequest);
    }

    @Operation(summary = "관심 호스트 조회")
    @GetMapping("/favoriteHost")
    public List<FavoriteHostResponse> getFavoriteHost(@RequestParam String puppyId){
        return puppyService.getFavoriteHost(puppyId);
    }

    @Operation(summary = "관심 호스트 추가")
    @PostMapping("/favoriteHost")
    public void addFavoriteHost(@RequestBody FavoriteHostRequest favoriteHostRequest){
        puppyService.addFavoriteHost(favoriteHostRequest);
    }

    @Operation(summary = "관심 호스트 삭제")
    @DeleteMapping("/favoriteHost")
    public void deleteFavoriteHost(@RequestBody FavoriteHostRequest favoriteHostRequest){
        puppyService.deleteFavoriteHost(favoriteHostRequest);
    }
}