package com.techacademy.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.transaction.Transactional;

import org.hibernate.annotations.Where;

import lombok.Data;

@Data
@Entity
@Table(name = "employee")
@Where(clause = "delete_flag = 0")
public class Employee {

    /** 主キー。自動生成 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** 名前。20桁。null不許可 */
    @Column(length = 20, nullable = false)
    private String name;
    
    /** フィールド名[削除フラグ]. */
    @Column(name = "delete_flag")
    private Integer delete_flag;
    
    /* 登録日時*/
   @Column(name = "created_at")
   private LocalDateTime created_at;
   
   /* 更新日時*/
   @Column(name = "updated_at")
   private LocalDateTime updated_at;
   
   // 
   @OneToOne(mappedBy="employee",cascade = CascadeType.ALL)
   private Authentication authentication;
   
   /** レコードが削除される前に行なう処理 */
   @PreRemove
   @Transactional
   private void preRemove() {
       // 認証エンティティからuserを切り離す
       if (authentication!=null) {
           authentication.setEmployee(null);
       }
   }
}