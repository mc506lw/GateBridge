# GateBridge

能让你在面板中运行 GateBridge 的项目

## 项目结构

```
GateBridge/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── gatebridge/
│   │   │           └── ServerCore.java
│   │   └── resources/
│   │       ├── gate.yml          # Gate 配置文件
│   │       └── gate.exe          # Gate 二进制文件（内置）
│   └── test/
│       ├── java/
│       └── resources/
├── gradle/
│   └── wrapper/
│       └── gradle-wrapper.properties
├── libs/                          # 外部依赖库
├── build.gradle                   # Gradle 构建配置
├── settings.gradle                # Gradle 设置
├── gradle.properties              # Gradle 属性配置
├── gradlew                        # Unix/Linux/Mac 启动脚本
└── gradlew.bat                    # Windows 启动脚本
```

## 功能特性

- **自动下载 Gate**：如果本地没有 Gate 二进制文件，会自动从 GitHub 下载最新版本
- **内置 Gate**：也可以将 Gate 二进制文件内置到项目中
- **跨平台支持**：支持 Windows、Linux 和 macOS
- **专业构建**：使用 Gradle 进行构建和依赖管理
- **配置管理**：自动提取和管理配置文件

## 快速开始

### 前置要求

- Java 11 或更高版本
- Gradle 8.5（或使用项目自带的 Gradle Wrapper）

### 构建前准备

**重要**：在首次构建之前，需要准备 Gate 二进制文件：

1. **下载 Gate**：
   - 访问 [Gate GitHub Releases](https://github.com/minekube/gate/releases)
   - 下载适合你操作系统的最新版本 Gate 二进制文件
   - Windows 用户下载 `gate-*-windows-amd64.exe`
   - Linux 用户下载 `gate-*-linux-amd64`
   - macOS 用户下载 `gate-*-darwin-amd64`

2. **重命名文件**：
   - 将下载的文件重命名为 `gate.exe`（Windows）或 `gate`（Linux/macOS）

3. **放置文件**：
   - 将重命名后的文件放入 `src/main/resources/` 目录
   - 或者直接放在项目根目录（运行时会自动提取）

4. **配置文件**：
   - 复制 `gate.yml.example` 为 `gate.yml`
   - 将 `gate.yml` 放入 `src/main/resources/` 目录
   - 或者直接放在项目根目录（运行时会自动提取）

### 构建项目

```bash
# 使用 Gradle Wrapper（推荐）
./gradlew build

# 或使用系统安装的 Gradle
gradle build
```

### 运行项目

```bash
# 使用 Gradle Wrapper
./gradlew run

# 或直接运行生成的 JAR
java -jar build/libs/GateBridge-1.0.0.jar
```

### 自动下载 Gate

如果本地没有 Gate 二进制文件，程序会在首次运行时尝试自动下载：

- Windows: 从 GitHub 下载 Windows 版本
- Linux: 从 GitHub 下载 Linux 版本
- macOS: 从 GitHub 下载 macOS 版本

**注意**：自动下载功能需要网络连接，并且可能受网络环境影响。如果下载失败，请手动下载并放置文件。

### 自定义 Gate 版本

编辑 `gradle.properties` 文件，修改 `gateVersion` 属性：

```properties
gateVersion=1.2.5
```

## 配置

Gate 的配置文件位于 `src/main/resources/gate.yml`。首次运行时，配置文件会被提取到工作目录。

主要配置项：

- `bind`: 监听地址（默认：0.0.0.0:25565）
- `lite.enabled`: 是否启用 Lite 模式（默认：true）
- `lite.routes`: 路由配置

详细配置请参考 [Gate 官方文档](https://gate.minekube.com/)。

## 开发

### 项目信息

- **Group**: com.gatebridge
- **Version**: 1.0.0
- **Main Class**: com.gatebridge.ServerCore

### 运行测试

```bash
./gradlew test
```

### 清理构建

```bash
./gradlew clean
```

## 许可证

本项目遵循 Gate 的许可证。

## 相关链接

- [Gate 官方网站](https://gate.minekube.com/)
- [Gate GitHub](https://github.com/minekube/gate)
- [Gate 文档](https://gate.minekube.com/guide/)
