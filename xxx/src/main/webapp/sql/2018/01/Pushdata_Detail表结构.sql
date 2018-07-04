USE [CMS_Cloudy_database]
GO
/****** Object:  Table [dbo].[Pushdata_Detail]    Script Date: 01/05/2018 14:15:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Pushdata_Detail](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[part_id] [bigint] NULL,
	[input_content] [nvarchar](200) NULL,
 CONSTRAINT [PK_Pushdata_Detail] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
