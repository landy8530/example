OPTIONS (skip=1,rows=128)
LOAD DATA
INFILE 'App_Customers_File.csv'
BADFILE 'App_Customers_File.bad'
DISCARDFILE 'App_Customers_File.dsc'
TRUNCATE
INTO TABLE ehtemp.BKOF7959_EMAIL_APP_LEAD
fields terminated by ','
TRAILING NULLCOLS 
(
  USER_PROFILE_ID ,
  LEAD_ID         ,
  APPLICATION_ID  ,
  EMAIL           ,
  CORRECTED_DO_NOT_MAIL ,
  EMAIL_FLAG            
)
