# Consultant -- 大学生身心健康 AI 咨询顾问

基于 **Spring Boot 3.2** + **LangChain4j** 构建的智能对话服务，专注于为在校大学生提供全维度身心健康咨询服务，涵盖心理疏导、作息调理、运动保健、压力管理、校园医疗等方向。

---

## 功能特性

- **AI 智能对话**：基于 LangChain4j 接入本地大模型，提供自然流畅的对话体验
- **流式响应**：使用 Spring WebFlux + Reactor 实现 SSE 流式输出，响应即时可见
- **上下文记忆**：集成 `MessageWindowChatMemory`，支持多轮会话记忆（最近 20 条消息）
- **系统角色预设**：通过 `system.txt` 定义专业的大学生健康顾问人设与交互规则
- **11 项核心服务**：心理咨询、情绪调节、运动健身、作息管理、医务室查询、健康科普、个性化方案推荐、一对一预约等
- **严格问答边界**：仅解答身心健康相关问题，非相关话题不予回应

---

## 技术栈

| 技术                   | 版本               | 说明                 |
| ---------------------- | ------------------ | -------------------- |
| Java                   | 21                 | 运行环境             |
| Spring Boot            | 3.2.5              | 核心框架             |
| LangChain4j            | 1.0.1-beta6        | AI 集成框架          |
| LangChain4j OpenAI     | 1.0.1-beta6        | OpenAI 兼容模型适配  |
| LangChain4j Reactor    | 1.0.1-beta6        | 流式响应支持         |
| Spring WebFlux         | 3.2.5              | 响应式 Web 支持      |
| Ollama + qwen3:0.6b    | --                 | 本地 LLM 模型        |

---

## 项目结构

```
consultant/
├── pom.xml                                          # Maven 项目配置
└── src/
    └── main/
        ├── java/org/itheima/
        │   ├── ConsultantApplication.java           # Spring Boot 启动类
        │   ├── Controller/
        │   │   └── ChatController.java              # 聊天接口控制器
        │   ├── aiservice/
        │   │   └── ConsultantService.java           # @AiService AI 服务接口
        │   └── config/
        │       └── CommonConfig.java                # Bean 配置（ChatMemory 等）
        └── resources/
            ├── application.yaml                     # 应用配置（LLM 连接参数）
            ├── system.txt                           # AI 系统提示词（人设 + 规则）
            └── static/
                └── index.html                       # 前端聊天页面
```

---

## 快速开始

### 前置条件

1. **JDK 21+** 已安装
2. **Maven 3.6+** 已安装
3. **Ollama** 已安装并运行，已拉取模型：

   ```bash
   ollama pull qwen3:0.6b
   ```

### 启动步骤

1. **克隆/进入项目目录**

   ```bash
   cd E:\JavaWeb\Web-AI-Code\consultant
   ```

2. **启动 Ollama 服务**（确保 11434 端口可访问）

3. **修改配置**（可选）

   编辑 `src/main/resources/application.yaml`，根据实际情况调整模型名称或 API 地址：


4. **启动应用**

   ```bash
   mvnw spring-boot:run
   ```

   或在 IDE 中直接运行 `ConsultantApplication.main()`。

5. **访问服务**

   - 前端页面：http://localhost:8080
   - 聊天 API：`GET /chat?memoryId={会话ID}&message={消息内容}`

---

## API 接口

### 流式聊天

```
GET /chat?memoryId={memoryId}&message={message}
```

| 参数       | 类型   | 必填 | 说明                               |
| ---------- | ------ | ---- | ---------------------------------- |
| memoryId   | String | 是   | 会话标识，用于区分不同用户/会话    |
| message    | String | 是   | 用户发送的消息内容                 |

**响应格式**：`text/html; charset=utf-8`，`Flux<String>` 流式输出。

**示例请求**：

```bash
curl "http://localhost:8080/chat?memoryId=user001&message=我最近总是失眠该怎么办？"
```

---

## 关键配置说明

### AI 人设与交互规则 (`system.txt`)

定义了 AI 助手的角色定位与行为约束，包括：

- **11 项核心服务**：心理咨询、情绪调节、运动健身、作息管理、医务室查询等
- **预约引导规则**：收集用户 7 项信息后确认预约
- **问答范围限制**：仅回答身心健康相关问题

可根据业务需求直接修改 `system.txt` 文件来调整 AI 行为。

### 会话记忆 (`CommonConfig.java`)

- 使用 `MessageWindowChatMemory`，保留最近 **20 条**对话记录
- 通过 `ChatMemoryProvider` 为每个 `memoryId` 创建独立记忆空间

---

## 运行测试

```bash
mvnw test
```

---

## 开发备忘

- LangChain4j 流式响应必须同时配置 `chatModel` 和 `streamingChatModel`
- `@AiService` 使用 `@MemoryId` 时需显式配置 `chatMemoryProvider`
- Controller 包名需使用小写（`controller`），避免 Spring 组件扫描遗漏

---

## 许可证

本项目仅供学习与内部使用。
