<?xml version="1.0" encoding="UTF-8"?>

<!-- New document created with EditiX at Thu May 19 12:20:43 CST 2011 -->

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html"/>
	
	<xsl:template match="/文章">
		<font   style="font-size:15">
			<xsl:apply-templates/>
		</font>
	</xsl:template> 
	
	<xsl:template match="段落">
			<xsl:apply-templates/>
			<br/>
	</xsl:template> 
	
	<xsl:template match="/文章/段落/重點">
         <font   style="color:red; font-size:18">

             <xsl:apply-templates/>
		 </font>
	</xsl:template>
		  
	<xsl:template match="/文章/段落/重點/重點">
		   <font   style="color:purple; font-size:20"> 
			 <xsl:apply-templates/>
	       </font>
	</xsl:template>
	  
	<xsl:template match="/文章/段落/重點/重點/重點">
		   <font   style="color:green; font-size:25"> 
	         <xsl:apply-templates/>
	       </font>
	</xsl:template>
	  
	<xsl:template match="/文章/段落/重點/重點/重點//重點">
		 <font   style="color:black; font-size:30"> 				 
			   <xsl:value-of select="." />
	       </font>
	</xsl:template>
		 
	<xsl:template match="註記" >
		<font style="color:blue"><sub><a title="{.}" >  註記  
			<xsl:number value="count(preceding::註記)+1"></xsl:number></a></sub>
		</font>
	</xsl:template>
	
</xsl:stylesheet>


