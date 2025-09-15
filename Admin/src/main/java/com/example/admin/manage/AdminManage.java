package com.example.admin.manage;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.admin.domain.Admin1;
import com.example.admin.mapper.Admin1Mapper;
import jakarta.annotation.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class AdminManage implements UserDetailsManager, UserDetailsPasswordService {
    @Resource
    private Admin1Mapper admin1Mapper;

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return null;
    }

    @Override
    public void createUser(UserDetails userDetails) {
        Admin1 admin1 = new Admin1();
        admin1.setUsername(userDetails.getUsername());
        admin1.setPassword(userDetails.getPassword());
        admin1.setEnabled(true);
        admin1Mapper.insert(admin1);
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<Admin1> qr = new QueryWrapper<>();
        qr.eq("username", username);
        Admin1 user = admin1Mapper.selectOne(qr);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }else{
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    user.getEnabled(),
                    true, //用户账号是否过期
                    true, //用户凭证是否过期
                    true, //用户是否未被锁定
                    authorities); //权限列表
        }
    }
}
