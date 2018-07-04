USE [CMS_Cloudy_database]
GO
/****** Object:  Table [dbo].[Process_Category]    Script Date: 05/18/2018 15:27:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Process_Category](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[category_sign] [nvarchar](100) NULL,
	[category_name] [nvarchar](200) NULL,
	[create_person] [nvarchar](50) NULL,
	[create_time] [datetime] NULL,
	[remark] [nvarchar](200) NULL,
	[cdefine1] [nvarchar](100) NULL,
	[cdefine2] [nvarchar](100) NULL,
	[cdefine3] [nvarchar](100) NULL,
	[cdefine4] [int] NULL,
	[cdefine5] [datetime] NULL,
 CONSTRAINT [PK_Process_Category] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
