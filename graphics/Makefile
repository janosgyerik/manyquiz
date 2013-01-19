src_dir:=src
hdpi_dir:=../res/drawable-hdpi
ldpi_dir:=../res/drawable-ldpi
mdpi_dir:=../res/drawable-mdpi

names:=$(patsubst $(src_dir)/%,%,$(wildcard $(src_dir)/*.png $(src_dir)/*.jpg))
hdpi_target:=$(patsubst %,$(hdpi_dir)/%,$(names))
ldpi_target:=$(patsubst %,$(ldpi_dir)/%,$(names))
mdpi_target:=$(patsubst %,$(mdpi_dir)/%,$(names))
market_target:=market/android.png

default: all

help:
	@cat Makefile.readme

hdpi: $(hdpi_target)

ldpi: $(ldpi_target)

mdpi: $(mdpi_target)

market: $(market_target)

all: hdpi ldpi mdpi market

clean: 
	rm $(hdpi_target) $(ldpi_target) $(mdpi_target) $(market_target)

$(hdpi_dir)/tab_%.png: $(src_dir)/tab_%.png
	convert -geometry 48x $< $@
	identify $@

$(ldpi_dir)/tab_%.png: $(src_dir)/tab_%.png
	convert -geometry 32x $< $@
	identify $@

$(mdpi_dir)/tab_%.png: $(src_dir)/tab_%.png
	convert -geometry 40x $< $@
	identify $@

$(hdpi_dir)/menu_%.png: $(src_dir)/menu_%.png
	convert -geometry 72x $< $@
	identify $@

$(ldpi_dir)/menu_%.png: $(src_dir)/menu_%.png
	convert -geometry 36x $< $@
	identify $@

$(mdpi_dir)/menu_%.png: $(src_dir)/menu_%.png
	convert -geometry 48x $< $@
	identify $@

$(hdpi_dir)/btn_%.png: $(src_dir)/btn_%.png
	convert -geometry 48x $< $@
	identify $@

$(ldpi_dir)/btn_%.png: $(src_dir)/btn_%.png
	convert -geometry 24x $< $@
	identify $@

$(mdpi_dir)/btn_%.png: $(src_dir)/btn_%.png
	convert -geometry 36x $< $@
	identify $@

$(hdpi_dir)/headerbtn_%.png: $(src_dir)/headerbtn_%.png
	convert -geometry 36x $< $@
	identify $@

$(ldpi_dir)/headerbtn_%.png: $(src_dir)/headerbtn_%.png
	convert -geometry 18x $< $@
	identify $@

$(mdpi_dir)/headerbtn_%.png: $(src_dir)/headerbtn_%.png
	convert -geometry 24x $< $@
	identify $@

$(hdpi_dir)/icon_%.png: $(src_dir)/icon_%.png
	convert -geometry 48x $< $@
	identify $@

$(ldpi_dir)/icon_%.png: $(src_dir)/icon_%.png
	convert -geometry 24x $< $@
	identify $@

$(mdpi_dir)/icon_%.png: $(src_dir)/icon_%.png
	convert -geometry 36x $< $@
	identify $@

$(hdpi_dir)/launcher_%.png: $(src_dir)/launcher_%.png
	convert -geometry 72x $< $@
	identify $@

$(ldpi_dir)/launcher_%.png: $(src_dir)/launcher_%.png
	convert -geometry 36x $< $@
	identify $@

$(mdpi_dir)/launcher_%.png: $(src_dir)/launcher_%.png
	convert -geometry 48x $< $@
	identify $@

$(hdpi_dir)/bullet_%.png: $(src_dir)/bullet_%.png
	convert -geometry 50x $< $@
	identify $@

$(ldpi_dir)/bullet_%.png: $(src_dir)/bullet_%.png
	convert -geometry 50x $< $@
	identify $@

$(mdpi_dir)/bullet_%.png: $(src_dir)/bullet_%.png
	convert -geometry 50x $< $@
	identify $@

market/android.png: $(src_dir)/launcher_main.png
	@mkdir -p $(@D)
	convert -geometry 512x $< $@
	identify $@

# eof
