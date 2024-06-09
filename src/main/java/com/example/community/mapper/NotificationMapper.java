package com.example.community.mapper;

import com.example.community.model.Notification;
import com.example.community.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NotificationMapper {
    @Insert("insert into notification (notifier_uid,receiver_uid,target_id,type,time) values (#{notifier_uid},#{receiver_uid},#{target_id},#{type},#{time})")
    void insert(Notification notification);

    @Update("update notification set isRead=1 where id=#{id}")
    void read(Integer id);

    @Select("select * from notification where receiver_uid = #{receiver_uid} ORDER BY time DESC limit #{offset},#{size}")
    List<Notification> findByReceiverUid_limit(@Param("receiver_uid") Integer receiver_uid, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from notification where receiver_uid = #{receiver_uid}")
    Integer countByReceiverUid(Integer receiver_uid);

    @Select("select count(1) from notification where receiver_uid = #{receiver_uid} and isRead = 0")
    Integer countUnreadByReceiverUid(Integer receiver_uid);
}
