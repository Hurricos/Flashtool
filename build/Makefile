tag := ubuntu:flashtool-builder
revision := 56528c0795676d9f3362e288a11435bd2abe00bf # 0.9.28.0


image: Dockerfile Makefile
	docker build . --tag $(tag); \
	touch $@;

image-%: Dockerfile Makefile
	docker build . --build-arg=revision=$* --tag $(addsuffix -$*, ${tag}); \
	touch $@;

flashtool-%-linux.tar.gz: image-%
	docker run --rm -it $(addsuffix -$*, ${tag}) /bin/sh -c 'cat /Flashtool/Deploy/flashtool-*-linux.tar.gz' > $@
