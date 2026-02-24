# GateBridge

能让你在面板（Java Docker 环境）中运行 Gate 的项目。

## 简介

GateBridge 是专为服务器面板（Docker 环境）设计的 Minecraft 代理服务器，基于 Gate 构建。

### 特性

- ✅ 面板友好，开箱即用
- ✅ Docker 优化
- ✅ 自动下载 Gate（支持多个国内加速镜像）
- ✅ 跨平台支持（Windows/Linux/macOS）
- ✅ 使用 Gradle 专业构建

## 快速开始

### 在面板中部署

1. **下载预构建的 JAR**：
   ```bash
   ./gradlew build
   # 生成的 JAR 位于 build/libs/GateBridge-1.0.0.jar
   ```

2. **准备文件**：
   - 下载 Gate 二进制文件（使用加速链接）：
     ```
     https://gh-proxy.org/https://github.com/minekube/gate/releases/download/v0.62.3/gate_0.62.3-linux-amd64
     ```
   - 重命名为 `gate`（Linux）或 `gate.exe`（Windows）
   - 复制 `gate.yml.example` 为 `gate.yml` 并修改配置

3. **上传到面板**：
   - 将 `GateBridge-1.0.0.jar`、`gate` 和 `gate.yml` 上传
   - 启动命令：`java -jar GateBridge-1.0.0.jar`

### 自动下载

首次运行时，如果本地没有 Gate 二进制文件，会自动从以下镜像下载：

- gh-proxy.org（全球加速）
- v6.gh-proxy.org（国内优选）
- cdn.gh-proxy.org（Fastly CDN）
- edgeone.gh-proxy.org（全球加速）
- hk.gh-proxy.org（香港线路）

## 配置

主要配置项（`gate.yml`）：

```yaml
config:
  bind: 0.0.0.0:25565
  lite:
    enabled: true
    routes:
      - host: localhost
        backend: localhost:25566
```

详细配置：[Gate 官方文档](https://gate.minekube.com/)

## 项目信息

- **Main Class**: com.gatebridge.ServerCore
- **Version**: 1.0.0
- **Java**: 11+

## 常见问题

**Q: 启动失败？**
A: 确保 Gate 二进制文件有执行权限，配置文件存在，Java 版本 ≥ 11

**Q: 如何更新 Gate？**
A: 替换 `gate` 或 `gate.exe` 文件即可

## 相关链接

- [Gate 官网](https://gate.minekube.com/)
- [Gate GitHub](https://github.com/minekube/gate)
- [Gate Releases](https://github.com/minekube/gate/releases)
