package com.restaurants.trazabilidad.infraestructur.driven_rp.adapter;
import com.restaurants.trazabilidad.domain.exception.ErrorFeignException;
import com.restaurants.trazabilidad.domain.interfaces.IUserServiceClient;
import com.restaurants.trazabilidad.domain.model.User;
import com.restaurants.trazabilidad.domain.utils.ConstantsDomain;
import com.restaurants.trazabilidad.infraestructur.feign.UserClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFeignServeImpl implements IUserServiceClient {
    private final UserClient userClient;

    @Override
    public User GetUser() {
        try {
            return userClient.getUser();
        } catch (FeignException e) {
            throw new ErrorFeignException((ConstantsDomain.COMUNICATION_ERROR_WITH_SERVICE) + e);
        }
    }
}