<?xml version="1.0" encoding="UTF-8"?>
<!-- New document created with EditiX at Thu May 19 12:20:43 CST 2011 -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html"/>
	<xsl:template match="/文章/段落">
		段落<xsl:number level="multiple" format="I"/>：<br/>
		<xsl:for-each select=".//重點">
			<xsl:number level="multiple" format="1-1"/> 
			<xsl:value-of select="." /><br/>
			<!--<xsl:apply-templates select="重點"/> -->
		</xsl:for-each> 
		<br/>
	</xsl:template>
	
	<!--
	<xsl:template match="//重點">
		<xsl:number level="multiple" format="1-1"/> 
		<xsl:value-of select="." /><br/>
        <xsl:apply-templates select="重點"/>
	</xsl:template>
		-->
	<!--
	<xsl:template match="/文章/段落/重點">
		<xsl:number level="multiple" format="1-1"/> 
		<xsl:value-of select="." /><br/>
        <xsl:apply-templates select="重點"/>
	</xsl:template>
		  
	<xsl:template match="/文章/段落/重點/重點">
		<xsl:number level="multiple" format="1-1"/> 
		<xsl:value-of select="." /><br/>
		<xsl:apply-templates select="重點"/>
	</xsl:template>
	  
	<xsl:template match="/文章/段落/重點/重點/重點">
		<xsl:number level="multiple" format="1-1"/>
		<xsl:value-of select="." /><br/>
	    <xsl:apply-templates select="重點"/>
	</xsl:template>
	  
	<xsl:template match="/文章/段落/重點/重點/重點//重點">
		<xsl:number level="multiple" format="1-1"/> 
		<xsl:value-of select="." /><br/>
		<xsl:apply-templates select="重點"/>
	</xsl:template>
	-->
</xsl:stylesheet>


