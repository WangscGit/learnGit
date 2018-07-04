USE [CMS_Cloudy_database]
GO
/****** Object:  Table [dbo].[Sys_Message_Detail]    Script Date: 03/21/2018 16:18:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Sys_Message_Detail](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[msg_main_id] [bigint] NOT NULL,
	[receiver] [nvarchar](50) NOT NULL,
	[state] [int] NOT NULL,
	[read_time] [datetime] NULL,
 CONSTRAINT [PK_Sys_Message_Detail] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
