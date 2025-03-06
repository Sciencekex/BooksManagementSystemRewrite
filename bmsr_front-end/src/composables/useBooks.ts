import { reactive, ref } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import { api } from '../api/config'
import type { Book } from '../types/books'

export function useBooks() {
    const books = reactive<Book[]>([])
    const add_dialog_visible = ref(false)
    const loading = ref(false)

    const book_form = reactive<Book & { id: string }>({
        book_number: '',
        book_name: '',
        book_type: '',
        book_prize: 0,
        author: '',
        book_publisher: '',
        id: '',
    })

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

    // 原有方法实现保持不变，只是移动到此处
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
    // ... 其他方法 ...

    return {
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
        resetForm
        // ... 暴露其他需要的方法和属性 ...
    }
}