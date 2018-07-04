USE [CMS_Cloudy_database]
GO

/****** Object:  Table [dbo].[file_img]    Script Date: 12/11/2017 16:00:07 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[file_img](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[img_url] [varchar](500) NULL,
	[img_name] [varchar](500) NULL,
	[img_sname] [varchar](500) NULL,
	[create_user] [varchar](500) NULL,
	[create_time] [datetime] NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO


