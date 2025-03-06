<template>
  <div style="margin: 0 auto; width: 80%; max-width: 1200px;">
    <h1 style="text-align: center">图书管理系统</h1>

    <!-- 添加图书按钮 -->
    <el-button type="primary" @click="add_dialog_visible = true">
      添加图书
    </el-button>

    <!-- 数据表格 -->
    <el-table
      v-loading="loading"
      :data="books"
      border
      style="margin: 20px auto;"
    >
      <el-table-column label="编号" prop="book_number" />
      <el-table-column label="书名" prop="book_name" />
      <el-table-column label="类型" prop="book_type" />
      <el-table-column label="价格" prop="book_prize" />
      <el-table-column label="作者" prop="author" />
      <el-table-column label="出版社" prop="book_publisher" />
      <el-table-column align="center" label="操作" width="200">
        <template #default="scope">
          <el-button
            size="small"
            type="primary"
            @click="handleEdit(scope.$index, scope.row)"
          >
            编辑
          </el-button>
          <el-button
            size="small"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <!-- 添加/编辑图书对话框 -->
  <el-dialog
    v-model="add_dialog_visible"
    :before-close="handleClose"
    :title="book_form.id ? '编辑图书' : '添加图书'"
    width="500px"
  >
    <el-form
      ref="ruleFormRef"
      :model="book_form"
      :rules="rules"
      label-width="100px"
      status-icon
    >
      <el-form-item label="编号" prop="book_number">
        <el-input v-model="book_form.book_number" />
      </el-form-item>

      <el-form-item label="书名" prop="book_name">
        <el-input v-model="book_form.book_name" />
      </el-form-item>

      <el-form-item label="类型" prop="book_type">
        <el-input v-model="book_form.book_type" />
      </el-form-item>

      <el-form-item label="价格" prop="book_prize">
        <el-input-number
          v-model="book_form.book_prize"
          :min="0"
          :precision="2"
          :step="0.1"
        />
      </el-form-item>

      <el-form-item label="作者" prop="author">
        <el-input v-model="book_form.author" />
      </el-form-item>

      <el-form-item label="出版社" prop="book_publisher">
        <el-input v-model="book_form.book_publisher" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="resetForm(ruleFormRef)">重置</el-button>
        <el-button type="primary" @click="submitForm(ruleFormRef)">
          {{ book_form.id ? '保存' : '添加' }}
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import axios from 'axios'
import { onMounted, reactive, ref } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessage, ElMessageBox } from 'element-plus'

interface Book {
  id: string
  book_number: string
  book_name: string
  book_type: string
  book_prize: number
  author: string
  book_publisher: string
}

// 创建axios实例
const api = axios.create({
  baseURL: 'http://localhost:5000',
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json',
  },
})

// 响应拦截器
api.interceptors.response.use(
  response => response.data,
  error => {
    ElMessage.error(error.response?.data?.message || '操作失败')
    return Promise.reject(error)
  },
)

const books = reactive<Book[]>([])
const dialogVisible = ref(false)
const add_dialog_visible = ref(false)
const ruleFormRef = ref<FormInstance>()
const loading = ref(false)

const rules = reactive<FormRules>({
  book_number: [{ required: true, message: '请输入图书编号', trigger: 'blur' }],
  book_name: [{ required: true, message: '请输入书名', trigger: 'blur' }],
  book_type: [{ required: true, message: '请输入类型', trigger: 'blur' }],
  book_prize: [
    { required: true, message: '请输入价格', trigger: 'blur' },
    { type: 'number', message: '价格必须为数字', trigger: 'blur' },
  ],
  author: [{ required: true, message: '请输入作者', trigger: 'blur' }],
  book_publisher: [{ required: true, message: '请输入出版社', trigger: 'blur' }],
}) as FormInstance

const book_form = reactive<Book & { id: string }>({
  book_number: '',
  book_name: '',
  book_type: '',
  book_prize: 0,
  author: '',
  book_publisher: '',
  id: '',
})

// 获取图书列表
const getBooks = async () => {
  try {
    loading.value = true
    const response = await api.get<{ results: Book[] }>('/books')
    books.splice(0, books.length, ...response.results)
    loading.value = false
  } catch (error) {
    loading.value = false
    console.error('获取图书列表失败:', error)
  }
}

// 删除数据
const handleDelete = async (index: number, row: Book) => {
  try {
    await ElMessageBox.confirm('确定要删除这本书吗？', '提示', {
      type: 'warning',
    })
    await api.delete(`/books/${row.id}`)
    ElMessage.success('删除成功')
    await getBooks()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除失败:', error)
    }
  }
}

// 编辑数据
const handleEdit = (index: number, row: Book) => {
  Object.assign(book_form, row)
  add_dialog_visible.value = true
}

// 对话框关闭前的确认
const handleClose = async (done: (cancel?: boolean) => void) => {
  try {
    await ElMessageBox.confirm('确定要关闭对话框吗？')
    resetForm(ruleFormRef.value)
    done()
  } catch {
    // 用户取消关闭
  }
}

// 表单提交事件
const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return

  try {
    await formEl.validate()
    const method = book_form.id ? 'put' : 'post'
    const url = book_form.id ? `/books/${book_form.id}` : '/books'

    await api[method](url, book_form)
    ElMessage.success('操作成功')
    add_dialog_visible.value = false
    resetForm(formEl)
    await getBooks()
  } catch (error) {
    if (error?.message !== 'validation failed') {
      console.error('提交失败:', error)
    }
  }
}

// 重置表单
const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
  Object.assign(book_form, {
    id: '',
    book_number: '',
    book_name: '',
    book_type: '',
    book_prize: 0,
    author: '',
    book_publisher: '',
  })
}

// 页面渲染后获取数据
onMounted(() => {
  getBooks()
})
</script>

<style scoped>
.el-input-number {
  width: 100%;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}
</style>