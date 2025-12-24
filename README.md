# SpawnWarps

Plugin de Spawn e Warps para Paper 1.21+

## Funcionalidades

- **Spawn**: Define um ponto de spawn global para o servidor
- **Warps**: Crie pontos de teleporte nomeados
- **Cooldown**: Sistema de cooldown configurável entre teleportes
- **Respawn automático**: Players respawnam no spawn ao morrer
- **First join**: Novos jogadores são teleportados ao spawn
- **Tab Complete**: Autocompletar nomes de warps nos comandos

## Comandos

| Comando | Descrição | Permissão |
|---------|-----------|-----------|
| `/spawn` | Teleporta ao spawn | Nenhuma |
| `/setspawn` | Define o spawn | `spawnwarps.setspawn` |
| `/warp <nome>` | Teleporta para uma warp | Nenhuma |
| `/setwarp <nome>` | Cria uma nova warp | `spawnwarps.setwarp` |
| `/delwarp <nome>` | Deleta uma warp | `spawnwarps.delwarp` |
| `/warps` | Lista todas as warps | Nenhuma |

## Permissões

| Permissão | Descrição | Padrão |
|-----------|-----------|--------|
| `spawnwarps.setspawn` | Definir spawn | OP |
| `spawnwarps.setwarp` | Criar warps | OP |
| `spawnwarps.delwarp` | Deletar warps | OP |
| `spawnwarps.bypass.cooldown` | Ignorar cooldown | OP |

## Configuração

```yaml
# Cooldown em segundos para teleportes
teleport-cooldown: 3

# Mensagens customizáveis com códigos de cor (&)
messages:
  prefix: "&8[&6SpawnWarps&8] &r"
  spawn-set: "&aSpawn definido com sucesso!"
  # ... outras mensagens
```

## Compilação

Requisitos:
- Java 21+
- Maven 3.8+

```bash
mvn clean package
```

O JAR será gerado em `target/spawn-warps-1.0.0.jar`

## Instalação

1. Compile o plugin ou baixe o JAR
2. Copie para a pasta `plugins/` do servidor
3. Reinicie o servidor
4. Configure em `plugins/SpawnWarps/config.yml`

## Tecnologias

- Paper API 1.21
- Adventure API (mensagens coloridas)
- Teleporte assíncrono (Paper)

## Autor

Gean - Portfolio de desenvolvimento Minecraft

---

**Licença**: MIT
