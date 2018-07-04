USE [CMS_Cloudy_database]
GO
/****** Object:  Table [dbo].[Push_Partdata]    Script Date: 12/13/2017 16:21:56 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Push_Partdata](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[part_id] [bigint] NULL,
	[input_content] [nvarchar](200) NULL,
	[seq_no] [int] NULL,
	[type] [char](10) NULL,
	[times] [int] NULL,
	[user_id] [bigint] NULL,
 CONSTRAINT [PK_Push_Partdata] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
