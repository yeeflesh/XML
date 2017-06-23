<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="3.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" indent="yes"/>
	
	<xsl:template match="products">
		<products>
			<xsl:apply-templates/>
		</products>
	</xsl:template>

	<xsl:template match="product">
		<product unitPrice="{unitPrice}" id="{id}">
			<name><xsl:value-of select="name"/></name>
		</product>
	</xsl:template>
	
</xsl:stylesheet>
