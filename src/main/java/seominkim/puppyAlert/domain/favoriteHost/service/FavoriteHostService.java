package seominkim.puppyAlert.domain.favoriteHost.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seominkim.puppyAlert.domain.favoriteHost.dto.request.FavoriteHostRequest;
import seominkim.puppyAlert.domain.favoriteHost.entity.FavoriteHost;
import seominkim.puppyAlert.domain.favoriteHost.repository.FavoriteHostRepository;
import seominkim.puppyAlert.domain.user.repository.UserRepository;
import seominkim.puppyAlert.global.exception.errorCode.ErrorCode;
import seominkim.puppyAlert.global.exception.exception.UserException;

@Service
@RequiredArgsConstructor
public class FavoriteHostService {

    private final FavoriteHostRepository favoriteHostRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public boolean isFavoriteHost(String puppyId, String hostId) {
        return favoriteHostRepository.existsByPuppyIdAndHostId(puppyId, hostId);
    }

    // 관심 HOST 추가
    @Transactional
    public Long addFavoriteHost(FavoriteHostRequest favoriteHostRequest){
        String puppyId = favoriteHostRequest.puppyId();
        String hostId = favoriteHostRequest.hostId();

        // 만약 이미 존재하면 예외처리
        if(favoriteHostRepository.existsByPuppyIdAndHostId(puppyId, hostId)){
            throw new UserException(ErrorCode.ALREADY_FAVORITE_HOST);
        }

        FavoriteHost favoriteHost = FavoriteHost.builder()
                .host(userRepository.findById(hostId).get())
                .puppy(userRepository.findById(puppyId).get())
                .build();

        return favoriteHostRepository.save(favoriteHost).getFavoriteHostId();
    }

    // 관심 HOST 삭제
    @Transactional
    public Long deleteFavoriteHost(FavoriteHostRequest favoriteHostRequest){
        String puppyId = favoriteHostRequest.puppyId();
        String hostId = favoriteHostRequest.hostId();

        // 만약 이미 삭제되었으면 예외처리
        if(!favoriteHostRepository.existsByPuppyIdAndHostId(puppyId, hostId)){
            throw new UserException(ErrorCode.DELETED_FAVORITE_HOST);
        }

        FavoriteHost favoriteHost = favoriteHostRepository.findByPuppyIdAndHostId(puppyId, hostId);
        favoriteHostRepository.delete(favoriteHost);

        return favoriteHost.getFavoriteHostId();
    }
}
