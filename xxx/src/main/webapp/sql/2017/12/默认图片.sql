INSERT INTO [CMS_Cloudy_database].[dbo].[file_img]
           ([img_url]
           ,[img_name]
           ,[img_sname]
           ,[create_user]
           ,[create_time])
     VALUES
           ('uploadImg\47abbfca91334243a8b73dc5964410af.PNG'
           ,'47abbfca91334243a8b73dc5964410af.PNG'
           ,'IMG_2149.PNG'
           ,'管理员'
           ,'2017-12-19 15:44:28.977');
           
update part_class set img_id=(select id from file_img fi where fi.img_name='47abbfca91334243a8b73dc5964410af.PNG');


