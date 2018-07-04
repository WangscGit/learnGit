USE [CMS_Cloudy_database]
GO
/****** Object:  Table [dbo].[task_user]    Script Date: 03/28/2018 10:26:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[task_user](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[task_def_key] [varchar](50) NULL,
	[process_inst_id] [varchar](50) NULL,
	[user_login_name] [varchar](100) NULL,
	[ut_type] [varchar](50) NULL,
	[is_finish] [varchar](50) NULL,
	[create_time] [datetime] NULL,
	[end_time] [datetime] NULL,
	[task_id] [bigint] NULL,
	[comment] [varchar](500) NULL,
	[is_agree] [varchar](50) NULL,
	[user_name] [varchar](50) NULL,
	[act_task_id] [varchar](50) NULL,
	[is_oneself] [varchar](50) NULL,
 CONSTRAINT [PK_task_user] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
