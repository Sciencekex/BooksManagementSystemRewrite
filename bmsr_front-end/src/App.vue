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
import { ref, onMounted } from 'vue'
import { ElMessageBox, FormInstance } from 'element-plus'
import { useBooks } from './composables/useBooks'

const {
  books,
  add_dialog_visible,
  loading,
  book_form,
  rules,
  getBooks,
  handleDelete,
  handleEdit,
  handleClose,
  submitForm,
  resetForm,
} = useBooks()

const ruleFormRef = ref<FormInstance>()

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