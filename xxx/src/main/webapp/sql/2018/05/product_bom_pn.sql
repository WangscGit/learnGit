USE [CMS_Cloudy_database]
GO

/****** Object:  Table [dbo].[product_bom_pn]    Script Date: 05/17/2018 14:06:37 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[product_bom_pn](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[productID] [int] NOT NULL,
	[PartNumber] [nvarchar](50) NOT NULL,
	[Numbers] [int] NULL,
	[SelectedTime] [datetime] NULL,
	[excel_name] [nvarchar](500) NULL,
	[excel_url] [nvarchar](500) NULL,
	[remark] [nvarchar](500) NULL,
	[sheet_name] [nvarchar](500) NULL,
	[version] [nvarchar](10) NULL
) ON [PRIMARY]

GO


