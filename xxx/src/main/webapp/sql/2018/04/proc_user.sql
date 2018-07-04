USE [CMS_Cloudy_database]
GO

/****** Object:  Table [dbo].[proc_user]    Script Date: 04/28/2018 17:49:24 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[proc_user](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[proc_def_id] [varchar](100) NULL,
	[login_name] [varchar](100) NULL,
	[user_name] [varchar](100) NULL,
	[task_def_key] [varchar](100) NULL,
	[ut_type] [varchar](50) NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO


