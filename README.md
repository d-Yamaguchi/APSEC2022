## AutoMig
This is an artifact of our paper titled _Two-Stage Patch Synthesis for API Migration from Single API Usage Example_ (APSEC 2022).

### Contents
`AutoMig.zip` contains the following items:
- `rawdata`: stores raw data of our experiment.
    - The directory `rawdata/[Android-SDK | CraftBukkit | Lucene]/Client` stores the client codes (e.g., `11.java`) and the applying-patches (e.g., `11_Patch.cocci`).
    - The directory `rawdata/[Android-SDK | CraftBukkit | Lucene]/Example` stores the API usage examples (e.g., `1.java`).
- `AutoMig-0.1-BENCHMARK.jar`: a pre-build jar of the artifact
- `ArtifactAPSEC22`: the source code of the artifact
- `LICENSE.docx`: specifies the software license agreement for evaluation

### Run `AutoMig-0.1-BENCHMARK.jar`
- It requires JDK11 and the following settings

#### Settings
1. Download `client_example.zip`, which is the set of client codes and API usage examples, through this [link](https://drive.google.com/file/d/1eOIHhnG7OD9RX8yELjOwuD7N2z-T6_pU/view?usp=sharing)
2. Download `jars.zip`, which is the jar files required for AutoMig's symbol solver, through this [link](https://drive.google.com/file/d/1rR7N0XI1Bh-2WXfGsyLTxqfnXZWfLH3W/view?usp=sharing)
3. Unzip them.

#### Usage Commands
Run benchmark:
```shell
$ java -jar AutoMig-0.1-BENCHMARK.jar --benchmark=aaa \
                               --update-example-path=bbb \
                               --target-dataset-path=ccc \
                               --path-to-jar=ddd
```
- `aaa` must be `Android-SDK`, `CraftBukkit`, or `Lucene`
- `bbb` must be the path to the set of API usage examples (e.g., `~/client_example/Android-SDK/Example`)
- `ccc` must be the path to the set of client codes (e.g., `~/client_example/Android-SDK/Client`)
- `ddd` must be the path to jar files that `jars.zip` stores (e.g., `~/jars`)

### About Source Code and Build
- The item `ArtifactAPSEC22` stores the source code for review.
- You can build through the following instructions.

#### Requirement to Build
- Apache Maven 3
- JDK11

#### Prepare dependencies
AutoMig requires `spoon-control-flow-0.0.2-SNAPSHOT` and `spoon-smpl-0.0.1`. You need to build them from source code.

1. clone & build
```shell
$ git clone https://github.com/INRIA/spoon.git
$ cd spoon
$ mvn install -DskipTests=true
```

2. copy `spoon/spoon-control-flow/target/spoon-control-flow-0.0.2-SNAPSHOT.jar` and `/spoon/spoon-smpl/target/spoon-smpl-0.0.1.jar` into `src/main/dependencies`

#### Build
3. Move into the directory `ArtifactAPSEC22`
4. Run `mvn validate` and `mvn install`


